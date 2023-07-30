# Run docker build with no cache
write-host "Building docker image with no cache"
write-host "$(Get-Location)"

# # build
# docker build --no-cache --progress=plain . -t tdp:java17 2>&1 | Tee-Object -FilePath build.log 

# Compose up
write-host "Starting docker compose"
docker-compose up -d 2>&1 | Tee-Object -FilePath compose.log