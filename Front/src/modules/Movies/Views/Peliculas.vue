<template>
  <div class="body">
    <div>
      <b>
        <h3>Peliculas</h3>
      </b>
    </div>

    <div class="bodybutton">
      <b-button v-b-modal.modal-save-movie class="btnadd">
        <b-icon icon="plus"></b-icon> Agregar pelicula
      </b-button>
    </div>

    <div>
      <b-row>
        <b-col>
          <div>
            <b-card title="Card Title" style="max-width: 20remm,max-height: 20remm " class="mb-2">
              <b-card-text>
                The Avengers (The Avengers: Los Vengadores en América Hispana y Marvel Los Vengadores en España) es una
                película de superhéroes basada en el equipo de superhéroes de Marvel Comics del mismo nombre. También es
                un cruce/secuela entre Iron Man, The Incredible Hulk, Iron Man 2, Thor y Captain America: The First
                Avenger.
              </b-card-text>
            
              <b-button href="#" variant="primary">Ver info</b-button>
            </b-card>
          </div>
        </b-col>
        <b-col>2</b-col>
        <b-col>3</b-col>
      </b-row>

      <button @click="fetchData">Obtener datos de la API</button>
    </div>


    <ModalSaveMovie />
  </div>
</template>
  
<script>
import ModalSaveMovie from '@/modules/Movies/Views/ModalSaveMovie.vue'
import axios from 'axios'
export default {
  components: { ModalSaveMovie },
  name: "pelis",
  data() {
    return {
      data: null
    }
  },
  methods: {
    fetchData() {

      axios.get('https://pokeapi.co/api/v2/pokemon/').then(response => {
        this.data = response.data;
        console.log(this.data.results);
      })
        .catch(error => {
          console.error('Error al obtener datos de la API', error);
        });
    }
  }
};
</script>
  
<style scoped>
.body {
  padding-left: 50px;
  padding-right: 50px;
  margin-top: 20px;
}

.bodybutton {
  text-align: end;
}

.btnadd {
  background-color: #089779; 
}
</style>