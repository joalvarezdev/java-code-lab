import * as path from "path"
import fs from "fs"

const BASE_PROJECT_ROOT = "../../../../" // Root path project

export const projectRootPath = path.resolve(__dirname, BASE_PROJECT_ROOT)

export const JAVA_PROJECT_PATH = "src/main/java"
export const RESOURCES_PROJECT_PATH = "src/main/resources"

export const modulesBasePath = path.join(projectRootPath, "modules")
export const templatesPath = path.join(__dirname, "../templates/entities")
export const moduleTemplatesPath = path.join(__dirname, "../templates/module")

// JAVA Projects paths
export const DEFAULT_PACKAGE_PREFIX = "com.joalvarez"

export function stringToKebabCase(data: string) {
  return data
    .replace(/([a-zA-Z])(?=[A-Z])/g, "$1-")
    .replace(/:/g, "-")
    .replace(/\s+/g, "-")
    .toLowerCase()
}

export function titleCase(name: string): string {
  return name
    .split(/[-_\s]+/)
    .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
    .join('')
}

export function getModulePath(moduleName: string): string {
  return path.join(modulesBasePath, moduleName)
}

export function getJavaPackagePath(packageName: string): string {
  return packageName.replace(/\./g, "/")
}

export function pathExists(path: string) {
  return fs.existsSync(path)
}

export function pathMake(path: string) {
  return fs.mkdirSync(path)
}

export function sanitizeToAlphanumeric(input: string): string {
  return input.toLowerCase().replace(/[^a-z0-9]/g, '')
}

export function getAvailableModules(): string[] {
  if (!fs.existsSync(modulesBasePath)) {
    return []
  }
  
  return fs.readdirSync(modulesBasePath, { withFileTypes: true })
    .filter(dirent => dirent.isDirectory())
    .map(dirent => dirent.name)
}

export function replaceDotBySlash(packageName: string): string {
  return packageName.replace(/\./g, "/")
}