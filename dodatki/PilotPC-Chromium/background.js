var retg
pilotpc.szukajMultimedialne=function(d,f)
{
	var queryInfo=new Object;
	queryInfo.active=true;
	queryInfo.currentWindow=true;
	chrome.tabs.query(queryInfo, function(a){
	chrome.tabs.sendMessage(a[0].id,[f,d]);
	
	});
}
pilotpc.intervel=setInterval(pilotpc.http,50);
