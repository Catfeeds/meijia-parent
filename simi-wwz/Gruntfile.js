module.exports = function (grunt) {

    grunt.initConfig({
        connect: {
            options: {
                port: 9000,
                hostname: 'localhost', //默认就是这个值，可配置为本机某个 IP，localhost 或域名
                livereload: 35729  //声明给 watch 监听的端口
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
                //{'Public/Default/css/common.css': 'Public/Default/css/common.less'},
                {'assets/css/iframe.css': 'assets/css/iframe.less'},
                ]
            },
            cleancss: {
                files: [
                        {'assets/css/iframe.min.css': 'assets/css/iframe.css'},
                        //{'Trunk/Public/Default/css/popup-min.css': 'Trunk/Public/Default/css/popup.css'}
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
                    'assets/css/*.css',
                    'assets/js/*.js',
                    'images/{,*/}*.{png,jpg,gif}'
                ]
            },
            scripts: {
                files: ['assets/css/iframe.less'],
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

    grunt.registerTask('serve', [
        'connect:server',
        'less',
        'watch'
    ]);
    grunt.registerTask('default', [
        'uglify'
    ]);
}