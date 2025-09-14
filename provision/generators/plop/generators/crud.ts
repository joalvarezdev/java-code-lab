import { Actions, PlopGeneratorConfig } from "node-plop"
import * as path from "path"
import * as fs from "fs"
import { JAVA_PROJECT_PATH, templatesPath, getAvailableModules, modulesBasePath, replaceDotBySlash } from "../utils"
import {
  AnswersCrud,
  CrudPromptNames,
  GoalChoices,
  IdTypeChoices
} from "./entities"

const helpers = require("handlebars-helpers")(["string"])

function getModulePackage(moduleName: string): string {

  const modulePath = path.join(modulesBasePath, moduleName)
  const srcPath = path.join(modulePath, JAVA_PROJECT_PATH)

  if (!fs.existsSync) {
    console.error(`Module path does not exist: ${modulePath}`)
    process.exit(1)
  }
  
  // Look for Application.java file recursively
  function findApplicationFile(dir: string): string | null {
    const files = fs.readdirSync(dir, { withFileTypes: true })
    
    for (const file of files) {
      const fullPath = path.join(dir, file.name)
      if (file.isDirectory()) {
        const result = findApplicationFile(fullPath)
        if (result) return result
      } else if (file.name.endsWith("Application.java")) {
        // Extract package from relative path
        const relativePath = path.relative(srcPath, dir)
        console.log("path para crear clases " + relativePath)
        return relativePath.replace(/[/\\]/g, ".")
      }
    }
    return null
  }
  
  // const packagePath = findApplicationFile(srcPath)
  // return packagePath || "com.joalvarez.examplemicroservices"
  return findApplicationFile(srcPath) || ""
}

export const crudGenerator: PlopGeneratorConfig = {
  description: "Create a CRUD for a named entity",
  prompts: [
    {
      type: "list",
      name: CrudPromptNames.module,
      message: "Select the module to add the CRUD classes:",
      choices: () => {
        const modules = getAvailableModules()
        return modules.length > 0 ? modules : ["No modules found"]
      },
      when: (answers: AnswersCrud) => answers.goal !== "module"
    },
    {
      type: "list",
      name: CrudPromptNames.goal,
      message: "Select a goal",
      choices: GoalChoices
    },
    {
      type: "input",
      name: CrudPromptNames.entityName,
      message: "What is the entity's name?",
      default: "sample",
      when: (answers: AnswersCrud) => answers.goal !== "client"
    },
    {
      type: "input",
      name: CrudPromptNames.pluralName,
      message: "What is the entity's plural name? (the db's table name)",
      default: (ans: AnswersCrud) => ans.entityName.concat("s").toLowerCase(),
      when: (answers: AnswersCrud) => answers.goal !== "client"
    },
    {
      type: "list",
      name: CrudPromptNames.entityId,
      message: "What is the entity's id type?",
      choices: IdTypeChoices,
      when: (answers: AnswersCrud) => answers.goal !== "client"
    },
    {
      type: "input",
      name: CrudPromptNames.package,
      message: "Which is the base package for the future files?",
      default: (answers: AnswersCrud) => getModulePackage(answers.module)
    },

    {
      type: "input",
      name: CrudPromptNames.clientName,
      message: "What is the client name?",
      default: "sample",
      when: (answers: AnswersCrud) => answers.goal === "client"
    },
    {
      type: "input",
      name: CrudPromptNames.baseUrl,
      message: "Enter the base API Url: ",
      default: "https://api.example.com",
      when: (answers: AnswersCrud) => answers.goal === "client",
      validate: (input) => input.startsWith("http")
        ? true
        : "URL must start with http:// or https://"
    }
  ],
  actions: (data) => {
    const answers = data as AnswersCrud

    // const modulePath = path.join(projectRoot, "modules", answers.module)
    const modulePath = path.join(modulesBasePath, answers.module)
    console.log("anwers.module: " + answers.module)
    console.log("modulePath: " + modulePath)
    const projectRootPath = path.join(modulePath, JAVA_PROJECT_PATH, replaceDotBySlash(answers.package))

    const actions: Actions = []

    let goals: Map<string, string> = new Map()

    switch (answers.goal) {
      case "controller":
        goals.set("Controller", "controller")
      case "service":
        goals.set("Service", "service")
      case "dao":
        goals.set("DAO", "data/dao")
    }

    if (answers.goal === "client") {
        goals.set("Client", "client")
    }

    if (answers.goal !== "client") {
      goals.set("Repository", "data/repository")
      goals.set("Mapper", "data/mapper")
      goals.set("", "data/domain")
      goals.set("DTO", "data/dto")
    }

    goals.forEach((goalPath, goal) => {
      const titleizedName = helpers.titleize(answers.entityName).replace(" ", "")
      const className = goal === "Client"
        ? `${titleizedName}${answers.clientName}Client`
        : titleizedName.concat(goal)
      actions.push({
        type: "add",
        templateFile: `${templatesPath}/${goal.length == 0 ? "Domain" : goal}.hbs`,
        path: path.join(projectRootPath, goalPath, className.concat(".java")),
        abortOnFail: false,
        data: {
          className: className,
          name: titleizedName
        }
      })
    })

    return actions
  }
}