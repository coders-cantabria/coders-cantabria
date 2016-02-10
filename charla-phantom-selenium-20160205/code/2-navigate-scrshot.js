var page = require("webpage").create();
url = "http://meneame.net";

page.viewportSize = {
  width: 1300,
  height: 768
};

page.open(url, function(status){
	
	page.render("page-full.jpg");
	
	var title = page.evaluate(function(){
		return document.title;
	});
	
	page.clipRect = {top: 0, left: 0, width: 300, height: 300};
	page.render("page-partial.jpg");
	
	console.log(title);
	
	phantom.exit();
});
