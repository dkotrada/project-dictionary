/**
* JetBrains Space Automation
* This Kotlin-script file lets you automate build activities
* For more info, refer to https://www.jetbrains.com/help/space/automation.html
*/

job("Run Shell Script") {
    container("ubuntu") {
        shellScript {
            interpreter = "/bin/bash"
            content = """
            echo Hello
            echo World
            """
        }
    }
}
