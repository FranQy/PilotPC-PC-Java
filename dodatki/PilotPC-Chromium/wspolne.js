var pilotpc=new Object();
pilotpc.dzialaj=function(dane, karta)
{
	
console.log('Pilot działaj');
if(karta==null)
return;
console.log(dane);
var uri;
console.log(karta)
if(karta.uri)
uri=karta.uri.host
else
uri=karta.document.URL

	console.log(uri);
	if(uri.search("youtube.com")>=0)
	{
	console.log(karta);
	//console.log(karta.uri.path);
	//console.log(dane);
		if(dane.akcja==2)//play/pauza
		{
			console.info('play');
			console.info(karta.document.getElementsByTagName('video').length);
			if(karta.document.getElementsByTagName('video').length>0)
			{
				if(karta.document.getElementsByTagName('video')[0].paused)
				karta.document.getElementsByTagName('video')[0].play()
				else
				karta.document.getElementsByTagName('video')[0].pause()
			}
			else
			{
				if(karta.document.getElementsByTagName('embed')[0].getPlayerState){
					if(karta.document.getElementsByTagName('embed')[0].getPlayerState()==1)
						karta.document.getElementsByTagName('embed')[0].pauseVideo()
					else
						karta.document.getElementsByTagName('embed')[0].playVideo()
				}
				else
				{
					console.warn(karta.document.getElementsByTagName('embed')[0]);
						karta.document.getElementsByTagName('embed')[0].pauseVideo()
						//karta.document.getElementsByTagName('embed')[0].playVideo()
				}
					
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
		else if(dane.akcja==3)//poprzedni
		{
		if(karta.document.getElementById('watch7-playlist-bar-prev-button'))
		karta.document.getElementById('watch7-playlist-bar-prev-button').click()
		}
		else if(dane.akcja==4)//następny
		{
		if(karta.document.getElementById('watch7-playlist-bar-next-button'))
		karta.document.getElementById('watch7-playlist-bar-next-button').click()
		}
		else if(dane.akcja==16)
		{
		
			if(karta.document.getElementsByTagName('video').length>0)
			{
				karta.document.getElementsByTagName('video')[0].currentTime+=-5;
			}
			else
			{
				karta.document.getElementsByTagName('embed')[0].seekTo(karta.document.getElementsByTagName('embed')[0].getCurrentTime()-5)
			}
		}
		else if(dane.akcja==17)
		{
		
			if(karta.document.getElementsByTagName('video').length>0)
			{
				karta.document.getElementsByTagName('video')[0].currentTime+=5;
			}
			else
			{
				karta.document.getElementsByTagName('embed')[0].seekTo(karta.document.getElementsByTagName('embed')[0].getCurrentTime()+5)
			}
		}
	}
	else if(uri.search("soundcloud.com")>=0)
	{
	console.log('play');
		if(dane.akcja==2)//play/pauza
		{
			karta.document.getElementsByClassName('playControl')[0].click()
		}
		else if(dane.akcja==3)//poprzedni
		{
			karta.document.getElementsByClassName('skipControl__previous')[0].click()
		}
		else if(dane.akcja==4)//następny
		{
			karta.document.getElementsByClassName('skipControl__next')[0].click()
		}
	}
	else if(uri.search("rmfon.pl")>=0)
	{
		if(dane.akcja==2)
		karta.document.getElementById('btn-play').click()
	}
	else if(uri.search("eskago.pl")>=0)
	{
		if(dane.akcja==2)
		karta.document.getElementById('_control_play').click()
	}
	else if(uri.search("radiozet.pl")>=0)
	{
		if(dane.akcja==2)
		karta.document.getElementByClassName('playpause')[0].click()
	}
	else if(karta.document.getElementsByTagName('video').length>0)
			{
			if(dane.akcja==2)
			{
				if(karta.document.getElementsByTagName('video')[0].paused)
				karta.document.getElementsByTagName('video')[0].play()
				else
				karta.document.getElementsByTagName('video')[0].pause()
			}
			}
	else if(karta.document.getElementsByTagName('audio').length>0)
			{
			if(dane.akcja==2)
			{
				if(karta.document.getElementsByTagName('audio')[0].paused)
				karta.document.getElementsByTagName('audio')[0].play()
				else
				karta.document.getElementsByTagName('audio')[0].pause()
			}
			}
}
pilotpc.http=function()
{
try{
var socket=new XMLHttpRequest();
//console.log('pilotpc http')
socket.open('GET', 'http://localhost:8753/dodatek', true);
socket.onload=function(){
	try{
	var dane=JSON.parse(socket.responseText);
	for(var i=0;i<dane.polecenia.length;i++)
	{
if(dane.polecenia[i].length>5)
	console.log('pol  '+dane.polecenia[i]);
	//pilotpc.dzialaj(dane.polecenia[i],pilotpc.szukajMultimedialne());
	pilotpc.szukajMultimedialne(dane.polecenia[i],pilotpc.dzialaj);

	}
	}
	catch(e)
	{
	console.log(e)
	}
}

socket.send()

}
catch(e)
{
console.log(e)
}

}