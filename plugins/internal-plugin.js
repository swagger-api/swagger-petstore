// Each plugin must have an id that is referenced in the .redocly.yaml file
const id = 'internal';

// This enables my IDE (VS Code) to use IntelliSense type completions.
/** @type {import('@redocly/openapi-cli').CustomRulesConfig} */
const decorators = {
  oas3: {
    // Each decorator has a name. We reference it in the .redocly.yaml file.
    // If we had a lot of decorators and rules in our plugin,
    // we would probably organize them into separate files.
    // Instead, we use an inline function here.
    'remove-internal-operations': () => {
      return {

        // We are inspecting each PathItem here.
        // The IntelliSense type completions are handy here, but we could "visit" Operation
        // and about 40+ other node types you can visit: https://github.com/Redocly/openapi-cli/blob/master/src/types/oas3.ts#L530
        PathItem: {
          // The options here are to execute when the visitor enters or leaves the node as it traverses the tree.
          leave(pathItem, ctx) {
            // Checks if the path itself is marked with x-internal
            if (pathItem['x-internal']) {
              // Then delete the path. However, delete works on an object property, so we need to delete from the parent object's pathItem property.
              // ctx is context.
              delete ctx.parent[ctx.key];
            }

            // delete any operations inside of a path marked with x-internal
            const operations = ['get', 'put', 'post', 'delete', 'options', 'head', 'patch', 'trace'];
            // The structure of the pathItem is that it may have operations as keys with the media type objects as their descriptions. We're going to check each operation key.
            for (const operation of operations) {
              if (pathItem[operation] && pathItem[operation]['x-internal']) {
                // This will delete only an operation. We can do this way because the operation is a property of the pathItem.
                delete pathItem[operation];
              }
            }

            // delete the path if there are no operations remaining in it
            if (Object.keys(pathItem).length === 0) {
              // In case all operations are removed from path items, we'll delete the path item itself.
              delete ctx.parent[ctx.key];
            }
          }
        }
      }
    },
  },
};

// This registers the id and decorators so that we can use our plugin.
module.exports = {
  id,
  decorators,
};