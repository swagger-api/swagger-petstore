package io.swagger.petstore.resource;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@OpenAPIDefinition(
        info = @Info(
                title = "Swagger Petstore - OpenAPI 3.1",
                summary = "Pet Store 3.1",
                version = "1.0.6",
                description = "This is a sample Pet Store Server based on the OpenAPI 3.1 specification.\nYou can find out more about\nSwagger at [http://swagger.io](http://swagger.io).",
                termsOfService = "http://swagger.io/terms/",
                contact = @Contact (
                        email = "apiteam@swagger.io"
                ),
                license = @License(
                        name = "Apache 2.0 AND (MIT OR GPL-2.0-only)",
                        identifier = "Apache-2.0 AND (MIT OR GPL-2.0-only)"
                ),
                extensions = {@Extension(name = "", properties = {@ExtensionProperty(name = "x-namespace", value = "swagger")})}
        ),
        servers = {
                @Server(
                        url = "/api/v31"
                )
        },
        externalDocs = @ExternalDocumentation(
                description = "Find out more about Swagger",
                url = "http://swagger.io"
        )
)
@Tags(
        {
                @Tag(
                        name = "pet",
                        description = "Everything about your Pets",
                        externalDocs = @ExternalDocumentation(
                                description = "Find out more",
                                url = "http://swagger.io"
                        )
                ),
                @Tag(
                        name = "store",
                        description = "Access to Petstore orders",
                        externalDocs = @ExternalDocumentation(
                                description = "Find out more about our store",
                                url = "http://swagger.io"
                        )
                ),
                @Tag(
                        name = "user",
                        description = "Operations about user"
                )

        }
)
@SecuritySchemes(
        {
                @SecurityScheme(
                name = "petstore_auth",
                type = SecuritySchemeType.OAUTH2,
                flows = @OAuthFlows(
                        implicit =  @OAuthFlow(
                            scopes = {
                                    @OAuthScope(name = "write:pets", description = "modify pets in your account"),
                                    @OAuthScope(name = "read:pets", description = "read your pets")
                            },
                            authorizationUrl = "https://petstore3.swagger.io/oauth/authorize"
                        )
                    )
                ),
                @SecurityScheme(
                        name = "api_key",
                        paramName = "api_key",
                        type = SecuritySchemeType.APIKEY,
                        in = SecuritySchemeIn.HEADER
                ),
                @SecurityScheme(
                        name = "mutual_tls",
                        type = SecuritySchemeType.MUTUALTLS
                )
        }
)
public class DefinitionResource {
}
