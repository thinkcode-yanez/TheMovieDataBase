# TheMovieDataBase
  TheMovieDatabase by thinkCode es una aplicacion que muestra informacion importante de todas las peliculas de decadas atras a la actualidad.
  
  ### Descripcion
 
 Esta aplicacion se trata de tener algunos datos de interes para los cinefilos. En la primera pantalla mostramos datos importantes como son
    
* Las 20 peliculas mas populares del momento
* Si el usuario "scrollea" hasta la pelicula #20 se cargaran automaticamente 20 peliculas mas en el conteo y asi sucesivamente hasta terminar con toda la data contenida en TMDB
* Una barra buscadora para teclear la pelicula deseada.
    
   ![1](https://i.postimg.cc/nh5fsvQ7/306159677-3518213645067985-129062215058710196-n.jpg)


Si el usuario teclea un nombre, este se ira buscando automaticamente y el resultado nos dara una lista completa de todas las peliculas que contengan dicha palabra o en su defecto el nombre exacto de la pelicula buscada.

![2](https://i.postimg.cc/52jMNPjb/305429342-3204414919872126-3043864609233318129-n.jpg)     ![3](https://i.postimg.cc/Qxf3DL8f/308090648-802003874449221-5208045403859439125-n.jpg)

El usuario tambien podra acceder a informacion mas especifica pulsando en cualquier portada que desee. Aqui tendra la opcion de "CALIFICAR" la pelicula , primeramente obteniendo un token y session validas las cuales podran obtenerse pulsando el icono "carita" de la toolbar. Una vez validado todo, y si este presiona el icono de la estrella roja, podra calificar del 1 al 10 y esta evaluacion se subira en automatico a TMDB.

![4](https://i.postimg.cc/Ls1KjnzW/308336632-421734303465955-8049034250186425290-n.jpg)     ![5](https://i.postimg.cc/m2LsKZsc/309693863-466360758799920-5212812506469857250-n.jpg)


## Conceptos aplicados en esta app.
    Aplicacion hecha con Kotlin en Android Studio

* RETROFIT
  Toda la informacion presentada en esta aplicacion fue posible gracias a la Api de The Movie Database Oficial en este link
  https://www.themoviedb.org/ 
* Recyclerview
* MVVM
* DAGGER/HILT
* ROOM (Para optimizar la app y las llamadas al servidor, almacenamos los datos de las peliculas ya visitadas y evitamos mas consumo de datos si se visitan de nuevo)
* SharedPreference
* Couroutines
* LiveData
* GLIDE
  
  Para mayor informacion a detalle vea el siguiente link
  https://youtu.be/rqHuoMmJdd0
 


