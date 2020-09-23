---
tags: [cool, demo, investigation]
---

<!-- theme: info -->

> ### Investigations into Stoplight capabilities
>
> A living list of investigated functionalities
![img](../../assets/images/banner_portal.png)

## Capabilities:

- [x] **Versioned releases** of documentation 
- [x] **Generates source-code** examples
- [x] **Internally publishable docs** with "direct-access" ACL

    ![img](../../assets/images/roles.png)
- [x] **External publishable docs**
- [x] github hooks integration
- [x] inline request demos
```json http
{
  "method": "get",
  "url": "https://todos.stoplight.io/todos"
}
```
- [x] PR based commits for web-portal changes
- [X] Markdown and supporting content creation through web-portal
- [x] User management and entitlement capabilities (Stoplight and fine-grained through github)
- [ ] Submodules do not break studio, but resolution of submodules is not supported in studio -- supposedly desktop app plus github CLI works, but not like they think we want. 

## Project structure table

Repo | Parent | Source Location | Content Type
---------|----------|---------|---------
 project root | N/A | github | markdown (md), apis (OpenAPI json/yaml), models (OpenAPI object definition json/yaml)
 docs | root | github, confluence | markdown (md)
 apis | root | github | apis (OpenAPI json/yaml)
 assets | root | github | images (png, jpg, gif, etc)
 app-source-code | root | github | source code, potentially any of the abobe