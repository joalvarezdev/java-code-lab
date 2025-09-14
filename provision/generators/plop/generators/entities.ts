export enum CrudPromptNames {
  "goal" = "goal",
  "module" = "module",
  "controllerType" = "controllerType",
  "entityName" = "entityName",
  "pluralName" = "pluralName",
  "entityId" = "entityId",
  "package" = "package",

  "clientName" = "clientName",
  "baseUrl" = "baseUrl"
}

export const GoalChoices = ["controller", "service", "dao", "repository"];
export const IdTypeChoices = ["Integer", "Long", "UUID", "String"];

export type AnswersCrud = { [P in CrudPromptNames]: string }