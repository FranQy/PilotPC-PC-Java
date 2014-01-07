chrome.runtime.onMessage.addListener(function(m){
pilotpc.dzialaj(m[1],this);
});