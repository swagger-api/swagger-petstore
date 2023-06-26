package io.swagger.petstore.utils;

import io.swagger.v3.oas.models.OpenAPI;
import org.apache.commons.lang3.StringUtils;

public class HandleAuthUrlProcessor {

    public void process(OpenAPI openAPI) {
        String oauthHost = System.getenv("SWAGGER_OAUTH_HOST");
        if (StringUtils.isBlank(oauthHost)) {
            return;
        }
        openAPI.getComponents().getSecuritySchemes().get("petstore_auth").getFlows().getImplicit().setAuthorizationUrl(oauthHost + "/oauth/authorize");
    }
}
