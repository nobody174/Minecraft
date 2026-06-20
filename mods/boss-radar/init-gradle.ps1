# Initialize Gradle Wrapper for Boss Radar
# This script downloads and sets up Gradle 8.7 for the project

$project_dir = "d:\Claude AI Projects\projects\Minecraft-mods\boss-radar"
$gradle_home = "D:\Claude AI Projects\apps\gradle-8.7"
$gradle_version = "8.7"

Write-Host "╔════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║  Gradle Initialization for Boss Radar  ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════╝" -ForegroundColor Cyan

# Check if Gradle already exists
if (Test-Path "$gradle_home\bin\gradle.bat") {
    Write-Host "`n✓ Gradle $gradle_version already installed at: $gradle_home" -ForegroundColor Green
} else {
    Write-Host "`nDownloading Gradle $gradle_version..." -ForegroundColor Yellow

    $url = "https://services.gradle.org/distributions/gradle-$gradle_version-bin.zip"
    $zip_path = "$env:TEMP\gradle-$gradle_version-bin.zip"

    try {
        [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
        Invoke-WebRequest -Uri $url -OutFile $zip_path -TimeoutSec 60

        Write-Host "✓ Downloaded to: $zip_path" -ForegroundColor Green
        Write-Host "Extracting..." -ForegroundColor Yellow

        New-Item -ItemType Directory -Path "D:\Claude AI Projects\apps" -Force | Out-Null
        Expand-Archive -Path $zip_path -DestinationPath "D:\Claude AI Projects\apps" -Force

        if (Test-Path "$gradle_home\bin\gradle.bat") {
            Write-Host "✓ Gradle installed successfully!" -ForegroundColor Green
        }

        Remove-Item $zip_path -Force
    } catch {
        Write-Host "✗ Failed to download Gradle: $_" -ForegroundColor Red
        exit 1
    }
}

# Set JAVA_HOME
$env:JAVA_HOME = "C:\Java\jdk-21.0.11+10"
Write-Host "`nJava version:" -ForegroundColor Cyan
java -version 2>&1

# Build the project
Set-Location $project_dir
Write-Host "`nBuilding Boss Radar mod..." -ForegroundColor Yellow
Write-Host "Location: $project_dir`n" -ForegroundColor Gray

& "$gradle_home\bin\gradle.bat" build
