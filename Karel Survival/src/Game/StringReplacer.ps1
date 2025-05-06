param(
    [string]$directoryPath,
    [string]$oldString,
    [string]$newString,
    [string]$fileFilter = "*.*"
)

$files = Get-ChildItem -Path $directoryPath -Filter $fileFilter -File

foreach ($file in $files) {
    $content = Get-Content -Path $file.FullName -Raw
    if ($content -match [regex]::Escape($oldString)) {
        $newContent = $content.Replace($oldString, $newString)
        Set-Content -Path $file.FullName -Value $newContent -Force
        Write-Host "Updated: $($file.FullName)"
    }
}
Write-Host "String replacement completed."