const webpack = require('webpack');

module.exports = {
    entry: './js/app.js',
    output: {
        path: './bin',
        filename: 'app.bundle.js'
    },
    module: {
        loaders: [{
            test: /\.js$/,
            exclude: /node_modules/,
            loader: 'babel-loader',
        }, {
            test: /\.css$/,
            loader: "style!css"
        }, {
            test: require.resolve("jquery"),
            loader: "expose?$!expose?jQuery"
        }]
    },

    devtool: "source-map",
    watch: true,
    watchOptions: {
        aggregateTimeout: 100
    }
};