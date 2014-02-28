var pilotpc_firefox = {
  onLoad: function() {
    // initialization code
    this.initialized = true;
    this.strings = document.getElementById("pilotpc_firefox-strings");
    //console.log(1);
  pilotpc_firefox.interval=setInterval(pilotpc.http,50);//czas do zmiany
  }
};

window.addEventListener("load", function () { pilotpc_firefox.onLoad(); }, false);





pilotpc.szukajMultimedialne=function(d,f)
{
		return f(d,window.Application.activeWindow.activeTab);
	
}
