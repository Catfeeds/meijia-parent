

var personHTML = Template7.templates.personTemplate({
    name: 'John Doen',
    age: 33,
    position: 'Developer',
    company: 'Apple'
});

//userlist: [
//        {
//            name: 'John',
//        },
//        {
//            name: 'Kyle',
//        },
// 
// ]

//快速下单
$$('.order-form').on('click', function() {
	myApp.alert('order-form');
});

//订单列表
$$('.order-list').on('click', function() {
	myApp.alert('order-list');
});