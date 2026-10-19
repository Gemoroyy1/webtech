$projectRoot = Split-Path $PSScriptRoot -Parent
& "$projectRoot/tools/eclipse/eclipse.exe" -data "$projectRoot/workspace"
