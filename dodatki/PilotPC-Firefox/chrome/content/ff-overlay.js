var pilotpc_firefox = {
  onLoad: function() {
    // initialization code
    this.initialized = true;
    this.strings = document.getElementById("pilotpc_firefox-strings");
    //console.log(1);
  pilotpc_firefox.interval=setInterval(pilotpc.http,50);//czas do zmiany
  },

  onMenuItemCommand: function(e) {
    var promptService = Components.classes["@mozilla.org/embedcomp/prompt-service;1"]
                                  .getService(Components.interfaces.nsIPromptService);
    promptService.console.log(window, this.strings.getString("helloMessageTitle"),
                                this.strings.getString("helloMessage"));
  },

  onToolbarButtonCommand: function(e) {
    // just reuse the function above.  you can change this, obviously!
    pilotpc_firefox.onMenuItemCommand(e);
  }
};

window.addEventListener("load", function () { pilotpc_firefox.onLoad(); }, false);

pilotpc_firefox.onFirefoxLoad = function(event) {
  document.getElementById("contentAreaContextMenu")
          .addEventListener("popupshowing", function (e) {
    pilotpc_firefox.showFirefoxContextMenu(e);
  }, false);
  
};

pilotpc_firefox.showFirefoxContextMenu = function(event) {
  // show or hide the menuitem based on what the context menu is on
  document.getElementById("context-pilotpc_firefox").hidden = gContextMenu.onImage;
};


pilotpc.szukajMultimedialne=function(d,f)
{
	if(pilotpc.szukajMultimedialneSpr(window.Application.activeWindow.activeTab))
	{
		return f(d,window.Application.activeWindow.activeTab);
	}
	console.log("szukaA");
	for(var i=0;i<window.Application.windows.length;i++)
	{
		if(pilotpc.szukajMultimedialneSpr(window.Application.windows[i].activeTab))
			return f(d,window.Application.windows[i].activeTab);
	}
	console.log("szukaB");
	for(var i=0;i<window.Application.windows.length;i++)
	{
		for(var i2=0;i2<window.Application.windows[i].tabs.length;i2++)
		{
			if(pilotpc.szukajMultimedialneSpr(window.Application.windows[i].tabs[i2]))
				return f(d,window.Application.windows[i].tabs[i2]);
		}
	}
	console.log("szukaC");
}

pilotpc.szukajMultimedialneSpr=function(tab)
{
return ((tab.uri.host=="youtube.com"||tab.uri.host=="www.youtube.com")&&tab.uri.path.substr(1,5)=="watch")||tab.uri.host=="soundcloud.com"||tab.document.getElementsByTagName('video').length>0||tab.document.getElementsByTagName('audio').length>0;
}
window.addEventListener("load", function () { pilotpc_firefox.onFirefoxLoad(); }, false);