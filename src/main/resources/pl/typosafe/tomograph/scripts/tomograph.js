;(function(undefined) {
  'use strict';

  if (typeof sigma === 'undefined')
    throw 'sigma is not declared';

  // Initialize package:
  sigma.utils.pkg('sigma.parsers');
  sigma.utils.pkg('sigma.utils');


  /**
   * This function loads a JSON file and creates a new sigma instance or
   * updates the graph of a given instance. It is possible to give a callback
   * that will be executed at the end of the process.
   *
   * @param  {string}       url      The URL of the JSON file.
   * @param  {object|sigma} sig      A sigma configuration object or a sigma
   *                                 instance.
   * @param  {?function}    callback Eventually a callback to execute after
   *                                 having parsed the file. It will be called
   *                                 with the related sigma instance as
   *                                 parameter.
   */
  sigma.parsers.tomograph = function(graph, sig, callback) {



    // Update the instance's graph:
    if (sig instanceof sigma) {
      sig.graph.clear();
      sig.graph.read(graph);

    // ...or instantiate sigma if needed:
    } else if (typeof sig === 'object') {
      sig.graph = graph;
      sig = new sigma(sig);

    // ...or it's finally the callback:
    } else if (typeof sig === 'function') {
      callback = sig;
      sig = null;
    }

         console.info(     sig
    )

    // Call the callback if specified:
    if (callback)
      callback(sig || graph);
  }
}).call(this);


var tomographCallback = function(s) {
      s.startForceAtlas2({gravity: 3});
      // We first need to save the original colors of our
      // nodes and edges, like this:
      s.graph.nodes().forEach(function(n) {
        n.originalColor = n.color;
      });
      s.graph.edges().forEach(function(e) {
        e.originalColor = e.color;
      });

      // When the stage is clicked, we just color each
      // node and edge with its original color.
      s.bind('clickStage', function(e) {
        s.graph.nodes().forEach(function(n) {
          n.color = n.originalColor;
        });

        s.graph.edges().forEach(function(e) {
          e.color = e.originalColor;
        });

        // Same as in the previous event:
        s.refresh();
      });
    }