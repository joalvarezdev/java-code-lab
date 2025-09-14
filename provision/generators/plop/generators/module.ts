import { Actions, PlopGeneratorConfig } from "node-plop"
import * as path from "path"
import { JAVA_PROJECT_PATH, RESOURCES_PROJECT_PATH, moduleTemplatesPath, projectRootPath, stringToKebabCase, DEFAULT_PACKAGE_PREFIX, sanitizeToAlphanumeric } from "../utils"
import {
  AnswersCrud,
  CrudPromptNames
} from "./entities"

export const moduleGenerator: PlopGeneratorConfig = {
  description: "Create a new module with base files",
  prompts: [
    {
      type: "input",
      name: CrudPromptNames.entityName,
      message: "What is the module's name?",
      default: "sample"
    },
    {
      type: "input",
      name: CrudPromptNames.package,
      message: "Enter the package suffix (will be com.joalvarez.{your input}):",
      default: (answers: any) => answers.entityName.toLowerCase().replace(/[^a-z0-9]/g, '')
    },
    {
      type: "input",
      name: "description",
      message: "Enter a description for the module:",
      default: (answers: any) => `${answers.entityName}`
    }
  ],
  actions: (data) => {
    const answers = data as AnswersCrud & { description: string }

    const modulePath = path.join(projectRootPath, "modules", stringToKebabCase(answers.entityName))

    const javaProjectPath = path.join(modulePath, JAVA_PROJECT_PATH)
    const resourcesProjectPath = path.join(modulePath, RESOURCES_PROJECT_PATH) 

    console.log("java project path" + javaProjectPath)
    console.log(resourcesProjectPath)


    const fullPackage = `${DEFAULT_PACKAGE_PREFIX}.${sanitizeToAlphanumeric(answers.package)}`
    const javaPath = path.join(javaProjectPath, fullPackage.replace(/\./g, "/"))

    console.log("java path" + javaPath)


    const actions: Actions = []

    // Create directory structure and base files
    actions.push(
      {
        type: "add",
        templateFile: `${moduleTemplatesPath}/build.gradle.kts.hbs`,
        path: path.join(modulePath, "build.gradle.kts"),
        data: {
          moduleName: stringToKebabCase(answers.entityName),
          packageName: fullPackage,
          description: answers.description
        }
      },
      {
        type: "add",
        templateFile: `${moduleTemplatesPath}/application.yml.hbs`,
        path: path.join(resourcesProjectPath, "application.yml"),
        data: {
          moduleName: answers.entityName.toLowerCase(),
          description: answers.description
        }
      },
      {
        type: "add",
        templateFile: `${moduleTemplatesPath}/Application.hbs`,
        path: path.join(javaPath, `${answers.entityName.split(/[-_\s]+/).map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()).join('')}Application.java`),
        data: {
          packageName: fullPackage,
          moduleName: answers.entityName
        }
      },
      {
        type: "add",
        templateFile: `${moduleTemplatesPath}/OpenApiConfig.hbs`,
        path: path.join(javaPath, "config", "OpenApiConfig.java"),
        data: {
          packageName: fullPackage,
          moduleName: answers.entityName
        }
      },
      {
        type: "add",
        templateFile: `${moduleTemplatesPath}/liquibase-changelog.yml.hbs`,
        path: path.join(resourcesProjectPath, "database", "liquibase-changelog.yml"),
        data: {
          moduleName: answers.entityName.toLowerCase()
        }
      },
      {
        type: "add",
        templateFile: `${moduleTemplatesPath}/configuration.sql.hbs`,
        path: path.join(resourcesProjectPath, "database", "configuration.sql"),
        data: {
          moduleName: answers.entityName.toLowerCase()
        }
      },
      {
        type: "add",
        templateFile: `${moduleTemplatesPath}/general.http.hbs`,
        path: path.join(resourcesProjectPath, "http", "general.http"),
      },
      {
        type: "add",
        templateFile: `${moduleTemplatesPath}/tmp.dockerfile.hbs`,
        path: path.join(modulePath, "Dockerfile"),
        data: {
          moduleName: stringToKebabCase(answers.entityName)
        }
      }
    )

    return actions
  }
}