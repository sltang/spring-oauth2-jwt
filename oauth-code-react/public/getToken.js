function getAccessToken(clientId, tokenUrl, redirectUri) {
	if (typeof(sessionStorage.accessToken) === 'undefined') {
		let params = (new URL(document.location)).searchParams;
		if (params.has('code')) {
			let code = params.get('code');
			if (typeof redirectUri === 'undefined') {
				if (window.location.port) {
					redirectUri = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port + '/';
				} else {
					redirectUri = window.location.protocol + '//' + window.location.hostname + '/';
				}
			}
			//to get access token, we authenticate with client id
			var options = {
				'grant_type':'authorization_code',
				'code':code,
		 		'redirect_uri':redirectUri,
				'client_id':clientId
			}

			$.ajax(tokenUrl, {
				type:'POST',
				async: false,
				beforeSend: function(xhr) { 
					xhr.setRequestHeader('Authorization', 'Basic ' + btoa(clientId + ':')); 
				},
				data: options, 
			}).done(function(token) {
				sessionStorage.setItem('accessToken', JSON.stringify(token));
			}).fail(function(err) {
				alert('Failed to get access token');
			});
		}
	}	
}

getAccessToken('code-react', 'https://localhost:8443/oauth/token');
