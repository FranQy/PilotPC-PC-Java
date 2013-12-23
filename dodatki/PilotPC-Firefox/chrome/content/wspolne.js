var pilotpc=new Object();
pilotpc.dzialaj=function(dane, karta)
{
console.log(dane);
	if(karta.uri.host.contains("youtube.com"))
	{
	//console.log(karta.uri.path);
	//console.log(dane);
		if(dane.akcja==3)//play/pauza
		{
			//console.info('play');
			if(karta.document.getElementsByTagName('video').length>0)
			{
				if(karta.document.getElementsByTagName('video')[0].paused)
				karta.document.getElementsByTagName('video')[0].play()
				else
				karta.document.getElementsByTagName('video')[0].pause()
			}
			else
			{
				if(document.getElementsByTagName('embed')[0].getPlayerState()==1)
					karta.document.getElementsByTagName('embed')[0].pauseVideo()
				else
					karta.document.getElementsByTagName('embed')[0].playVideo()
			/*	console.log('len:'+karta);
				console.log('len:'+karta.document);
				console.log('len:'+karta.document.getElementsByClassName('ytp-button-pause'));
				console.log('len:'+karta.document.getElementsByClassName('ytp-button-pause').length);
				console.log(karta.document.getElementsByClassName('ytp-button-play')[0].onclick);
				if(karta.document.getElementsByClassName('ytp-button-pause').length>0)
					karta.document.getElementsByClassName('ytp-button-pause')[0].onclick(new Object())
				else
					karta.document.getElementsByClassName('ytp-button-play')[0].onclick(new Object())*/
			}
		}
		else if(dane.akcja==4)//poprzedni
		{
		karta.document.getElementById('watch7-playlist-bar-prev-button').click()
		}
		else if(dane.akcja==5)//następny
		{
		karta.document.getElementById('watch7-playlist-bar-next-button').click()
		}
	}
}
pilotpc.http=function()
{
var socket=new XMLHttpRequest();
socket.open('GET', 'http://localhost:8753/dodatek', false);
socket.send()
console.log(socket.responseText);
var dane=JSON.parse(socket.responseText);
for(var i=0;i<dane.polecenia.length;i++)
{
try{
pilotpc.dzialaj(dane.polecenia[i],pilotpc_firefox.szukajMultimedialne());
}
catch(e){
console.error('error'+e);
}
}
setTimeout(pilotpc.http,50);//czas do zmiany
}