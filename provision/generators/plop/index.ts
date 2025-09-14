import { NodePlopAPI } from "node-plop"
import helpers from "handlebars-helpers"
import { crudGenerator, moduleGenerator } from "./generators"
import { stringToKebabCase, titleCase } from "./utils"

const HANDLEBARS_OPTIONS_ARG_INDEX = 2

export default function plop(plop: NodePlopAPI) {
  const stringHelpers = require("handlebars-helpers")(["string"])
  
  plop.setHelper("eq", helpers().eq)
  plop.setHelper("slugify", (txt: string) => {
    return txt
      .replace(/([a-z])([A-Z])/g, '$1-$2')
      .toLowerCase()
  })
  plop.setHelper("stringToKebabCase", stringToKebabCase)
  plop.setHelper("title", stringHelpers.titleize)
  plop.setHelper("cleanClassName", titleCase)
  
  plop.setHelper("env", function(name: string, defaultValue?: any) {
    const isDefaultProvided = arguments.length > HANDLEBARS_OPTIONS_ARG_INDEX && typeof arguments[1] === 'string'

    return isDefaultProvided 
      ? `\\$\{${name}:${defaultValue}\}`
      : `\\$\{${name}\}`
  })
  
  plop.setHelper("var", function(name: string, defaultValue?: any) {
    const isDefaultProvided = arguments.length > HANDLEBARS_OPTIONS_ARG_INDEX && typeof arguments[1] === 'string'
    if (isDefaultProvided) {
      return `$\{${name}:${defaultValue}\}`
    } else {
      return `$\{${name}\}`
    }
  })
  
  plop.setHelper("concat", function(...args: any[]) { 
    return args.slice(0, -1).join("") 
  })
  
  plop.setGenerator("crud", crudGenerator)
  plop.setGenerator("module", moduleGenerator)
}