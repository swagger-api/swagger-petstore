<!-- theme: danger -->

> #### 💡 403 error calling apis
>
> If you are receiving a 403 forbidden error when invoking IIN APIs from a registered domain, you probably need to pass the an OAuth bearer token in your request headers.

<!--
type: tab
title: Token API
-->

# Get token!

Sweet, beautiful content, ready to blow your readers' minds.
```yaml http
{
  "method": "get",
  "url": "http://todos.stoplight.io/token",
}
```


<!--
type: tab
title: Request with token
-->

# Example request with token!

With more mind-blowing material. Really. Just amazing, grade-A stuff.
```yaml http
{
  "method": "get",
  "url": "http://todos.stoplight.io/todos",
  "headers": {"Authorization": "Bearer $token"}
}
```

<!-- type: tab-end -->
