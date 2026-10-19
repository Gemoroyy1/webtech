$projectRoot = Split-Path $PSScriptRoot -Parent
$env:JAVA_HOME = (Get-ChildItem "$projectRoot/tools/eclipse/plugins" -Filter javac.exe -Recurse | Select-Object -First 1).Directory.Parent.FullName
$env:CATALINA_HOME = "$projectRoot/tools/apache-tomcat-9.0.123"
$env:Path = "$env:JAVA_HOME/bin;$projectRoot/tools/apache-maven-3.9.16/bin;$env:Path"
Set-Location $projectRoot
Write-Host 'Среда готова: java, javac, mvn. Tomcat: http://localhost:8081'
