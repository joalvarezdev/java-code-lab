rootProject.name = "java-code-lab"

include("shared")
include("send-emails")
include("web-socket")

project(":shared").projectDir = file("modules/shared")
project(":send-emails").projectDir = file("modules/send-emails")
project(":web-socket").projectDir = file("modules/web-socket")
