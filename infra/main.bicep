@description('Specifies the name of project.')
param name string

@description('Specifies the name of the container app.')
param containerAppName string= '${name}-aca-app'

@description('Specifies the name of the container app environment.')
param containerAppEnvName string = '${name}-aca-env'

@description('Specifies the name of the log analytics workspace.')
param containerAppLogAnalyticsName string= '${name}-aca-loganalytics'

@description('Specifies the location for all resources.')
param location string = resourceGroup().location

// ACR Section
@description('Specifies the docker container image to deploy.')
param containerImage string 

@description('Specifies the name of the container registry.')
param registryserver string
@description('Specifies the name of the container registry.')
param containerRegistryName string

@description('Specifies the username of the container registry.')
param acrUsername string

@description('Specifies the password of the container registry.')
@secure()
param acrPassword string

var aca_secret={
  acr_username: acrUsername
  acr_password:acrPassword
}

// ACA Section
@description('Specifies the container port.')
param targetPort int = 80

param aca_volume_mountPath string 
param acenv_fileshareName string
param acenv_storage_account_name string
param acenv_volumes_storagename string
param aca_volumes_name string
param acenv_fileshare_resource_group string

var aca_volumes={
    name: aca_volumes_name
    storageName: acenv_volumes_storagename
    storageType: 'AzureFile'
  }
var aca_volumeMounts={
      mountPath: aca_volume_mountPath
      volumeName: aca_volumes.name
    }

@description('Number of CPU cores the container can use. Can be with a maximum of two decimals.')
@allowed([
  '0.25'
  '0.5'
  '0.75'
  '1'
  '1.25'
  '1.5'
  '1.75'
  '2'
])
param cpuCore string = '0.5'

@description('Amount of memory (in gibibytes, GiB) allocated to the container up to 4GiB. Can be with a maximum of two decimals. Ratio with CPU cores must be equal to 2.')
@allowed([
  '0.5'
  '1'
  '1.5'
  '2'
  '3'
  '3.5'
  '4'
])
param memorySize string = '1'

@description('Minimum number of replicas that will be deployed')
@minValue(0)
@maxValue(25)
param minReplicas int = 4

@description('Maximum number of replicas that will be deployed')
@minValue(0)
@maxValue(25)
param maxReplicas int = 7

resource containerAppLogAnalytics 'Microsoft.OperationalInsights/workspaces@2022-10-01' = {
  name: containerAppLogAnalyticsName
  location: location
  properties: {
    sku: {
      name: 'PerGB2018'
    }
  }
}
 resource azure_storage 'Microsoft.Storage/storageAccounts@2022-09-01' existing = {
  name: acenv_storage_account_name
  scope: resourceGroup(acenv_fileshare_resource_group)
}

resource containerAppEnv 'Microsoft.App/managedEnvironments@2022-11-01-preview' = {
  name: containerAppEnvName
  location: location
  properties: {
    appLogsConfiguration: {
      destination: 'log-analytics'
      logAnalyticsConfiguration: {
        customerId: containerAppLogAnalytics.properties.customerId
        sharedKey: containerAppLogAnalytics.listKeys().primarySharedKey
      }
    }
    
  }

}
resource storage 'Microsoft.App/managedEnvironments/storages@2022-11-01-preview' = {
  name: aca_volumes.storageName 
  parent: containerAppEnv
  properties: {
    azureFile: {
      accessMode: 'ReadWrite'
      accountKey: azure_storage.listKeys().keys[0].value
      accountName: azure_storage.name
      shareName: acenv_fileshareName
    }
}
}

resource containerApp 'Microsoft.App/containerApps@2022-06-01-preview' = {
  name: containerAppName
  location: location
  properties: {
    managedEnvironmentId: containerAppEnv.id
    configuration: {
      secrets: [
        {
          name: containerRegistryName
          value: aca_secret.acr_password
        }
      ]
      ingress: {
        external: true
        targetPort: targetPort
        allowInsecure: false
        traffic: [
          {
            latestRevision: true
            weight: 100
          }
        ]
      }
      registries: [
        {
          passwordSecretRef: containerRegistryName
          server: registryserver
          username: acrUsername
        }
      ]
    }
    template: {
      // revisionSuffix: 'rev'
      containers: [
        {
          name: containerAppName
          image: containerImage
          resources: {
            cpu: json(cpuCore)
            memory: '${memorySize}Gi'
          }
          volumeMounts: [
            {
              mountPath: aca_volumeMounts.mountPath
              volumeName: aca_volumes.name
            }
          ]
        }
      ]
      scale: {
        minReplicas: minReplicas
        maxReplicas: maxReplicas
      }
      volumes: [
        {
          name: aca_volumes.name
          storageName: aca_volumes.storageName
          storageType: aca_volumes.storageType
        }
      ]

    }
  }
}

output containerAppFQDN string = containerApp.properties.configuration.ingress.fqdn
