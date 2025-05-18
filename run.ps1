$projectRoot = $PSScriptRoot
$srcDir = Join-Path $projectRoot "src"
$binDir = Join-Path $projectRoot "bin"
$sourcesFile = Join-Path $projectRoot "sources.txt"

if (!(Test-Path $binDir)) {
    New-Item -ItemType Directory -Path $binDir | Out-Null
}

Get-ChildItem -Recurse -Filter *.java -Path $srcDir | ForEach-Object {
    $relativePath = $_.FullName -replace '.*?\\src\\', 'src/'
    $escapedPath = $relativePath -replace '\\', '/'
    "`"$escapedPath`"" | Out-File -Append -FilePath $sourcesFile -Encoding ascii
}

javac -d $binDir "@$sourcesFile"
Remove-Item $sourcesFile

Write-Host "Compilation complete."

java -cp $binDir Main