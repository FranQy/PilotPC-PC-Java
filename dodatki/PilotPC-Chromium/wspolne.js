var pilotpc=new Object();
pilotpc.dzialaj=function(dane, karta)
{
if(karta==null)
return;
console.log(dane);
var uri;
console.log(karta)
if(karta.uri)
uri=karta.uri.host
else
uri=karta.document.URL
	if(uri.search("youtube.com")>=0)
	{
	//console.log(karta.uri.path);
	//console.log(dane);
		if(dane.akcja==3)//play/pauza
		{
			console.info('play');
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
	if(uri.search("soundcloud.com")>=0)
	{
	console.log('play');
		if(dane.akcja==3)//play/pauza
		{
			karta.document.getElementsByClassName('playControl')[0].click()
		}
		else if(dane.akcja==4)//poprzedni
		{
			karta.document.getElementsByClassName('skipControl__previous')[0].click()
		}
		else if(dane.akcja==5)//następny
		{
			karta.document.getElementsByClassName('skipControl__next')[0].click()
		}
	}
	else if(karta.document.getElementsByTagName('video').length>0)
			{
				if(karta.document.getElementsByTagName('video')[0].paused)
				karta.document.getElementsByTagName('video')[0].play()
				else
				karta.document.getElementsByTagName('video')[0].pause()
			}
	else if(karta.document.getElementsByTagName('audio').length>0)
			{
				if(karta.document.getElementsByTagName('audio')[0].paused)
				karta.document.getElementsByTagName('audio')[0].play()
				else
				karta.document.getElementsByTagName('audio')[0].pause()
			}
}
pilotpc.http=function()
{
try{
var socket=new XMLHttpRequest();

socket.open('GET', 'http://localhost:8753/dodatek', false);

socket.send()
var dane=JSON.parse(socket.responseText);
}
catch(e)
{
console.log(e)
}
for(var i=0;i<dane.polecenia.length;i++)
{

//pilotpc.dzialaj(dane.polecenia[i],pilotpc.szukajMultimedialne());
pilotpc.szukajMultimedialne(dane.polecenia[i],pilotpc.dzialaj);

}
setTimeout(pilotpc.http,50);//czas do zmiany
}