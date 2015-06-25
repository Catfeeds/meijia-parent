

// Callbacks to run specific code for specific pages, for example for About page:
myApp.onPageBeforeInit('mine', function (page) {

     if (!isLogin()) {
    	 console.log("mine is not logined");
    	 mainView.router.loadPage('about.html'); //load another page with auth form
         return false; //required to prevent default router action
     }
});