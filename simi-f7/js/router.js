
// Callbacks to run specific code for specific pages, for example for About page:
myApp.onPageBeforeInit('mine', function (page) {

    console.log(page);

    // run createContentPage func after link was clicked
    $$('.create-page').on('click', function () {
        createContentPage();
    });
});