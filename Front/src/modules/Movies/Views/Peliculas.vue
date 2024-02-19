<template>
  <div class="body">
    <div class="loading-overlay" v-if="isLoading">
      <div class="loading-spinner"></div>
    </div>
    <template>
      <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
          <b>
            <h3>Peliculas <b-icon icon="camera-reels"></b-icon></h3>
          </b>
        </div>
        <div class="bodybutton">
          <b-button v-b-modal.modal-save-movie class="btnadd">
            <b-icon icon="plus"></b-icon> Registrar pelicula
          </b-button>
        </div>
      </div>
    </template>
    <br>
    <div class="mb-4">
      <b-row v-if="data && data.data && data.data.length > 0">
        <TransitionGroup name="roll" tag="div" class="d-flex" lg="3" md="6" sm="12">
          <b-col v-for="(movie, index) in paginatedItems" :key="index">
            <b-card :title="movie.name" style="width: 100%; height: 17rem" draggable="true">
              <b-card-text>
                <b>Género:</b> {{ movie.genero }}<br>
                <b>Descripción:</b> {{ movie.description }}<br>
              </b-card-text>
              <template #footer>
                <div class="icono">
                  <b-button variant="faded" @click="OpenEditModal(movie)"><b-icon icon="pencil"></b-icon></b-button>
                  <b-button variant="faded" style="color: red;" @click="deleteMovie(movie.id)"><b-icon
                      icon="trash"></b-icon></b-button>
                </div>
              </template>
            </b-card>
          </b-col>
        </TransitionGroup>
      </b-row>

      <div class="text-center" v-if="!paginatedItems.length">
        <p>No se encontraron películas registradas</p>
      </div>
    </div>

    <div class="d-flex justify-content-between align-items-center">
      <div>
        <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage" aria-controls="my-table"
          class="mt-3"></b-pagination>

        <p class="mt-3">Página actual: {{ currentPage }}</p>
      </div>

      <div class="mb-3">
        <label for="perPageSelect" class="mr-2">Películas por página:</label>
        <b-form-select v-model="perPage" id="perPageSelect" :options="perPageOptions"></b-form-select>
      </div>
    </div>

    <ModalSaveMovie @movie-updated="fetchData" />
    <ModalUpdateMovie ref="modalUpdateMovie" :movie="selectedMovie" @movie-updated="fetchData" />

  </div>
</template>

<script>
import ModalSaveMovie from '@/modules/Movies/Views/ModalSaveMovie.vue';
import ModalUpdateMovie from './ModalUpdateMovie.vue';
import axios from 'axios'
import Swal from 'sweetalert2';


export default {
  components: { ModalSaveMovie, ModalUpdateMovie },
  name: "pelis",
  data() {
    return {
      isLoading: false,
      data: null,
      selectedMovie: null,
      perPage: 4,
      currentPage: 1,
      perPageOptions: [4, 8, 12, 16]
    }
  },
  computed: {
    rows() {
      return this.data ? this.data.data.length : 0;
    },
    paginatedItems() {
      const start = (this.currentPage - 1) * this.perPage;
      const end = start + this.perPage;
      return this.data ? this.data.data.slice(start, end) : [];
    }
  },
  methods: {
    fetchData() {
      axios.get('http://localhost:8090/api-movieBack/')
        .then(response => {
          this.data = response.data;
        })
        .catch(error => {
          console.error('Error al obtener datos de la API', error);
        });
    },
    OpenEditModal(movie) {
      this.selectedMovie = movie;
      this.$bvModal.show('modal-update-movie');
    },
    async deleteMovie(id) {
      const confirmed = await Swal.fire({
        title: "¿Estás seguro de eliminar la película?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#008c6f',
        cancelButtonColor: '#e11c24',
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: 'Cancelar',
      });

      if (confirmed.isConfirmed) {
        try {
          this.isLoading = true;
          const response = await axios.delete(`http://localhost:8090/api-movieBack/peliculas/${id}`);
          if (response.data.error) {
            console.error(response.data.message);
          } else {
            Swal.fire({
              title: 'Eliminada',
              text: 'La película se eliminó correctamente',
              icon: 'success',
            });
            this.fetchData();
          }
        } catch (error) {
          const { data } = error;
          this.$swal.fire({
            icon: "error",
            text: data?.text ? data.text : "Error interno",
            timer: 3000,
          });
        }
        finally {
          this.isLoading = false;
        }
      }
    },
  },
  mounted() {
    this.fetchData();
  },
};
</script>

<style>
.body {
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 10px;
}

@media screen and (max-width: 768px) {
  .body {
    padding-left: 10px;
    padding-right: 10px;
  }
}


.btnadd {
  background-color: #089779;
  padding-right: px;
}

.icono {
  text-align: end;
}

.card-text-scroll {
  max-height: 150px;
  overflow-y: auto;
  /* añade una barra de desplazamiento vertical si es necesario */
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  /* Semi-transparent black overlay */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
  /* Make sure it's on top of everything */
}

.loading-spinner {
  border: 4px solid rgba(255, 255, 255, 0.3);
  border-top: 4px solid #fff;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
}

.roll-enter-active, .roll-leave-active {
  transition: transform 0.5s;
}

.roll-enter, .roll-leave-to {
  transform: translateY(30px);
  opacity: 0;
}
</style>
