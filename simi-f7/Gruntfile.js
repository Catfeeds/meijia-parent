module.exports = function (grunt) {

    grunt.initConfig({
        connect: {
            options: {
                port: 8000,
                hostname: 'localhost', //默认就是这个值，可配置为本机某个 IP，localhost 或域名
                livereload: 35730  //声明给 watch 监听的端口
            },

            server: {
                options: {
                    open: true, //自动打开网页 http://
                    base: [
                        '.'  //主目录 设置为当前目录
                    ]
                }
            }
        },


         less: {
            compile: {
                files: [
                {'css/my-app.css': 'css/my-app.less'},
                ]
            },
            cleancss: {
                files: [
                        {'css/my-app-min.css': 'css/my-app.css'},
                    ],
                options: {
                    cleancss: true
                }
            }
        },

        watch: {
            livereload: {
                options: {
                    livereload: '<%=connect.options.livereload%>'  //监听前面声明的端口  35729
                },

                files: [  //下面文件的改变就会实时刷新网页
                    '*.html',
                    '**/*.html',
                    '**/*.css',
                    '**/*.js',
                    '**/*.{png,jpg,gif}'
                ]
            },
            scripts: {
                files: ['**/*.less'],
                tasks: ['less']
            }
        },

        uglify: {
            alias: {
                'kit/util': 'kit/util'
            },
            target: {
                expand: true,
                cwd: 'Public/Default/js/plugins/resumechart/',
                src: 'resumechart-act.js',
                dest:'Public/Default/js/dest/'
            }
        }

    });

    require('load-grunt-tasks')(grunt); //加载所有的任务
    //require('time-grunt')(grunt); 如果要使用 time-grunt 插件

    grunt.registerTask('dev', [
        'connect:server',
        'less',
        'watch'
    ]);
    grunt.registerTask('default', [
        'uglify'
    ]);
}