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
		return f(d,window.Application.activeWindow.activeTab);
	
}

window.addEventListener("load", function () { pilotpc_firefox.onFirefoxLoad(); }, false);