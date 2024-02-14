param(
    [string]$templateFile=".\infra\main.bicep",
    # read pararmeters from .env file
    $envParameters = $(Get-Content .\.env | ConvertFrom-StringData),
    # get name from parameters
    [string]$name=$envParameters.name,
    [string]$location=$envParameters.location,
    # get createResourceGroup from parameters
    [string]$createResourceGroup=$envParameters.createResourceGroup,
    
    [string]$subscriptionId=$env:subscriptionId,

    # get resource group name from parameters
    # if createResourceGroup is false, use the resource group name from parameters else
    # use the name "name-rg"
    [string]$resourceGroupName=$(if ($createResourceGroup -eq "false") {
      $envParameters.resourceGroup} else {
        "$name-rg"}), 
    [string]$containerRegistryName=$envParameters.containerRegistryName,

    # Image configuration
    [string]$registryserver=$envParameters.registryserver,
    [string]$repository=$envParameters.repository,
    [string]$imagetag=$envParameters.deploymentImageTag,
    # Container image in the format of <container registry>/<image name>:<image tag>
    #TODO: use the registry server from the parameters
    [string]$containerImage="$($registryserver)/$($repository):$($imagetag)",
        
    #Azure Container Environment Related values
    [string]$acenv_fileshare_resource_group=$envParameters.acenv_fileshare_resource_group,
    [string]$acenv_storage_account_name=$envParameters.acenv_storage_account_name,
    [string]$acenv_fileshareName=$envParameters.acenv_fileshareName,
    [string]$acenv_volumes_storagename=$envParameters.acenv_volumes_storagename, 
    
    #Azure Container Apps Related values
    [string]$aca_volumes_name=$envParameters.aca_volumes_name,
    [string]$aca_volume_mountPath=$envParameters.aca_volume_mountPath ,
    [string]$aca_targetPort=$envParameters.targetPort,
    [string]$aca_maxReplicas=$envParameters.maxReplicas,
    [string]$aca_minReplicas=$envParameters.minReplicas
    )
    
# set default subscription
az account set --subscription $subscriptionId

#Write Container registry
write-host "Container Registry: $containerRegistryName"

# Container registry username 
[string]$acrUsername=$(az acr credential show -n $containerRegistryName --query username -o tsv)


# # Container registry password
# # TODO: use SecureString
# # TODO: use keyvault to store password
[string]$acrPassword=$(az acr credential show -n $containerRegistryName --query "passwords[0].value" -o tsv)

# # Create a resource group with azure cli
# Create a resource group if $createResourceGroup is true
write-host "Create Resource Group: $createResourceGroup"

if ($createResourceGroup -eq "true") {
  Write-Host "Creating resource group $resourceGroupName"
  az group create --name $resourceGroupName --location $location
}
Write-Host "Resource Group Name: $resourceGroupName"
# Test message
# Write-Host "****** TestMassage: $env:TestMessage ******"
# Image configuration
Write-Host "****** Image configuration ******"
Write-Host "Registry Server: $registryserver"
Write-Host "Repository: $repository"
Write-Host "Image Tag: $imagetag"
Write-Host "Container Image: $containerImage"

# Azure Container Apps Related values
Write-Host "****** Azure Container Apps Related values ******"
Write-Host "Azure Container Apps Volumes Name: $aca_volumes_name"
Write-Host "Azure Container Apps Volume Mount Path: $aca_volume_mountPath"
Write-Host "Azure Container Apps Target Port: $aca_targetPort"

# Azure Container Environment Related values
Write-Host "******Azure Container Environment Related values ******"
Write-Host "Azure Container Environment Fileshare Resource Group: $acenv_fileshare_resource_group"
Write-Host "Azure Container Environment Storage Account Name: $acenv_storage_account_name"
Write-Host "Azure Container Environment Fileshare Name: $acenv_fileshareName"
Write-Host "Azure Container Environment Volumes Storage Name: $acenv_volumes_storagename"

# Common values
Write-Host "****** Common values *******"
Write-Host "Location: $location"
Write-Host "Container Registry Name: $containerRegistryName"
Write-Host "Container Registry Server: $registryserver"
Write-Host "Container Registry Username: $acrUsername"

# deploy the template
az deployment group create `
  -g $resourceGroupName `
  --template-file $templateFile `
  --parameters name=$name `
    containerImage=$containerImage `
    targetPort=$aca_targetPort `
    aca_volume_mountPath=$aca_volume_mountPath `
    aca_volumes_name=$aca_volumes_name `
    maxReplicas=$aca_maxReplicas `
    minReplicas=$aca_minReplicas `
    acenv_fileshare_resource_group=$acenv_fileshare_resource_group `
    acenv_storage_account_name=$acenv_storage_account_name `
    acenv_fileshareName=$acenv_fileshareName `
    acenv_volumes_storagename=$acenv_volumes_storagename `
    location=$location `
    containerRegistryName=$containerRegistryName `
    registryserver=$registryserver `
    acrUsername=$acrUsername `
    acrPassword=$acrPassword `
  --query properties.outputs.fqdn.value