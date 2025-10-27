export default function GetFetch(route, returnTo){
    const BASE_URL = 'http://localhost:8080/api'
    useEffect(() => {
    fetch(`${BASE_URL}${route}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('Error' + response);
        }
        return response.json();
      })
      .then(data => returnTo(data))
      .catch(error => console.error(error));
  }, []);
}