#!/usr/bin/python

import ghApiClient

def getLastReleaseTag():
    content = ghApiClient.readUrl('repos/swagger-api/swagger-petstore/releases')
    for l in content:
        draft = l["draft"]
        tag = l["tag_name"]
        if str(draft) != 'True' and tag.startswith("swagger-petstore-v3-"):
            return tag
    print ("NO RELEASE TAG FOUND, using default swagger-petstore-v3-1.0.17")
    return "swagger-petstore-v3-1.0.17"
# main
def main():
    result = getLastReleaseTag()
    print (result)

# here start main
main()
