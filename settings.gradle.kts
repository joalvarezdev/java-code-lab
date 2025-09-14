rootProject.name = "java-code-lab"

include("shared")
include("send-emails")

project(":shared").projectDir = file("modules/shared")
project(":send-emails").projectDir = file("modules/send-emails")
