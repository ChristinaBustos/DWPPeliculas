<template>
  <div class="body">
    <div>
      <b>
        <h3>Peliculas</h3>
      </b>
    </div>

    <div class="bodybutton">
      <b-button v-b-modal.modal-save-movie class="btnadd">
        <b-icon icon="camera-reels"></b-icon> Registrar pelicula
      </b-button>
    </div>
    <br>
    <div>
      <b-row v-if="data && data.data">
        <b-col v-for="(movie, index) in data.data" :key="index" lg="3" md="6" sm="12">
          <b-card :title="movie.name" style="max-width: 20rem; height: 15rem" class="mb-2">
            <b-card-text>
              <b>Género:</b> {{ movie.genero }}<br>
              <b>Descripción:</b> {{ movie.description }}<br>
            </b-card-text>

            <template  #footer>
            <div class="icono">
              <b-button variant="faded" @click="edit"><b-icon icon="pencil"></b-icon></b-button>
            </div>
            </template>
          </b-card>
        </b-col>
      </b-row>
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
      axios.get('http://localhost:8090/api-movieBack/')
        .then(response => {
          console.log(response.data.data);
          this.data = response.data;
        })
        .catch(error => {
          console.error('Error al obtener datos de la API', error);
        });
    },

    edit(){
      console.log("Jala el boton para editar xd");
    }
  },
  mounted() {
    this.fetchData();
  },
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

.icono {
  text-align: end;
}
</style>
