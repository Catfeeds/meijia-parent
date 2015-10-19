module.exports = function(grunt) {

	grunt.initConfig({
		pkg : grunt.file.readJSON('package.json'),
		concat : {
			
			options : {
				separator : ';',
			},
			
			jslib : {
				src : [ 'js/lib/moment-with-locales.min.js',
					    'js/echarts/echarts.js','js/echarts/gauge.js',
					    'js/lib/appframework.min.js', 'js/lib/mobiscroll.custom-2.16.1.min.js'],
				dest : 'js/u-lib.js',
			},
			
			jsmain : {					
				// 源文件路径
				src : [ 'js/main.js', 'js/require-data.js', 'js/utils.js', 'js/validate-reg.js', 'js/service-date-select.js', 'js/remind-date-select.js'],
				dest : 'js/u-main.js'
			},
			
			jsu : { 						
				// 源文件路径
				src : ['js/index.js', 'js/login.js', 'js/order/*.js', 'js/order/**/*.js', 'js/user/*.js' ],
				dest : 'js/u.js'
			},
			
//			cssf7: {  
//			      src: ['css/framework7.css'],//当前grunt项目中路径下的src/css目录下的所有css文件  
//			      dest: 'css/framework7.min.css'  //生成到grunt项目路径下的dist文件夹下为all.css
//			},
//			
//			cssf7themes: {  
//			      src: ['css/framework7.themes.css'],//当前grunt项目中路径下的src/css目录下的所有css文件  
//			      dest: 'css/framework7.themens.min.css'  //生成到grunt项目路径下的dist文件夹下为all.css
//			},
//			
//			cssmyapp: {  
//			      src: ['css/my-app.css'],//当前grunt项目中路径下的src/css目录下的所有css文件  
//			      dest: 'css/framework7.themens.min.css'  //生成到grunt项目路径下的dist文件夹下为all.css
//			}			

		},

		uglify : {
			options: {
		        compress: {
		          drop_console: true
		        }
		     },
		     
			lib : {

				files : {
					'js/u-lib.min.js' : [ 'js/u-lib.js' ]
				}
			},
			
			main : {

				files : {
					'js/u-main.min.js' : [ 'js/u-main.js' ]
				}
			},
			u : {

				files : {
					'js/u.min.js' : [ 'js/u.js' ]
				}
			}

		},
		
		cssmin : {
//			cssf7: {  
//		        src: 'css/framework7.css',//将之前的all.css  
//		        dest: 'css/framework7.min.css'  //压缩  
//		      } ,
			cssf7themes: {  
		        src: 'css/framework7.themes.css',//将之前的all.css  
		        dest: 'css/framework7.themes.min.css'  //压缩  
		      } ,
			      
			cssmyapp: {  
		        src: 'css/my-app.css',//将之前的all.css  
		        dest: 'css/my-app.min.css'  //压缩  
		      } ,			      
		},
		
		//增加版本号
		cache : {
			jsUlib : {
				options : {},
				assetUrl : 'js/u-lib.min.js',
				files : {
					'tmp' : [ 'index1.html' ],
				},
			},			
			jsUmain : {
				options : {},
				assetUrl : 'js/u-main.min.js',
				files : {
					'tmp' : [ 'index1.html' ],
				},
			},
			jsU : {
				options : {},
				assetUrl : 'js/u.min.js',
				files : {
					'tmp' : [ 'index1.html' ],
				},
			},
			css : {
				options : {},
				assetUrl : 'css/my-app.min.css',
				files : {
					'tmp' : [ 'index1.html' ],
				},
			},
		},

	});
	
	grunt.loadNpmTasks('grunt-css');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('asset-cache-control');
	grunt.registerTask('default', ['concat', 'uglify', 'cssmin', 'cache']);

}