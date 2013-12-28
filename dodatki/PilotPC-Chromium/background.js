var retg
pilotpc.szukajMultimedialne=function(d,f)
{
	/*var ret=null;
	//console.log(1);
	chrome.tabs.getCurrent(function(tab){
	//console.log(2);
	//console.log(tab);
	//console.info(pilotpc.szukajMultimedialneSpr(tab))
	if(pilotpc.szukajMultimedialneSpr(tab))
	chrome.tabs.sendMessage(0,[d,f],function(){
	
	f(d,this)
	})
	})*/
	//console.log(ret);
	
	/*chrome.tabs.getCurrent(function(tabA){
	if(pilotpc.szukajMultimedialneSpr(tabA))
	chrome.tabs.sendMessage(tabA.id,[f,d])
	else*/
	chrome.windows.getAll(function(okna){
	for(var x=0;x<okna.length;x++)
	{
	chrome.tabs.getAllInWindow(okna[x].id,function(tab){
	for(var i=0;i<tab.length;i++)
	{
	//console.log(2);
	//console.log(tab[i]);
	if(pilotpc.szukajMultimedialneSpr(tab[i]))
	{
	console.log([tab[i].id,[f,d]]);
	chrome.tabs.sendMessage(tab[i].id,[f,d])
	}
	}
	})
	}});//});
}

pilotpc.szukajMultimedialneSpr=function(tab)
{
return ((tab.url.search("youtube.com")>6&&tab.url.search("youtube.com")<13)&&tab.url.search("watch")>=0||(tab.url.search("soundcloud.com")>6&&tab.url.search("soundcloud.com")<13));
}
pilotpc.intervel=setInterval(pilotpc.http,50);
