<template>
  <div  style="height: 1500px"  class="content">
    <div
      class="content my-5 mx-5"
      v-show="showElement"
    >
      <b-card >
        <b-form
          draggable
          @dragstart="handleDragStart"
          @dragover.prevent
          @drop="handleDrop"
        >
          <b-row>
            <b-col>
              <label for="">Nombre: *</label>
              <b-input
                type="text"
                v-model="pelicula.name"
                required
                placeholder="Nombre de la pelicula"
              ></b-input>
            </b-col>
            <b-col>
              <label for="">Genero: *</label>
              <b-input
                type="text"
                v-model="pelicula.genero"
                required
                placeholder="Genero de la pelicula"
              ></b-input>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <label for="">Fecha de publicación: *</label>
              <b-form-datepicker
                v-model="pelicula.publishDate"
                type="date"
                class="form-control"
                required
                aria-describedby="input-live-help input-live-feedback"
              />
            </b-col>
            <b-col>
              <label for="">Director: *</label>
              <b-input
                type="text"
                v-model="pelicula.director"
                required
                placeholder="Director de la pelicula"
              ></b-input>
            </b-col>
          </b-row>

          <b-row>
            <b-col>
              <label for="">Descripción: *</label>
              <b-form-textarea
                id="textarea"
                v-model="pelicula.description"
                placeholder="Describe la pelicula..."
                rows="3"
                max-rows="6"
              ></b-form-textarea>
            </b-col>
          </b-row>
        </b-form>
      </b-card>
    </div>

    <div
      @dragover.prevent
      @drop="handleDrop"
      class="my-5 mx-5"
    >
      <b-card>
        <p style="color: grey" v-if="!isDragging">
          Arrastra acá para guardar la película
        </p>
        <p style="color: grey" v-if="isDragging">
          Suelta aquí para guardar la película
        </p>
      </b-card>
    </div>

    <b-row
      style="
        margin-left: 50px;
        margin-right: 50px;
        margin-top: 50px;
        margin-bottom: 50px;
      "
      class=""
      v-if="data && data.data"
    >
      <TransitionGroup name="roll" tag="div" class="d-flex d-fixed">
        <b-col v-for="(movie, index) in data.data" :key="index">
          <b-card :title="movie.name" style="height: 100%; width: auto">
            <b-card-text>
              <b>Género:</b> {{ movie.genero }}<br />
              <b>Director:</b> {{ movie.director }}<br />
              <b>Fecha de publicación:</b> {{ movie.publishDate }}<br />
              <b>Descripción:</b> {{ movie.description }}<br />
            </b-card-text>
            <template #footer>
              <div class="icono">
                <b-button
                  variant="faded"
                  style="color: red"
                  @click="deleteMovie(movie.id)"
                  ><b-icon icon="trash"></b-icon
                ></b-button>
              </div>
            </template>
          </b-card>
        </b-col>
      </TransitionGroup>
    </b-row>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import axios from "axios";
export default {
  name: "dragform",
  data() {
    return {
      data: null,
      pelicula: {
        name: "",
        genero: "",
        publishDate: null,
        description: "",
        director: "",
      },
      isDragging: false,
      showElement: true,
      lastScrollPosition: 0
    };
  },
  methods: {
    async save() {
      Swal.fire({
        title: "¿Estás seguro de registrar la pelicula?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#008c6f",
        cancelButtonColor: "#e11c24",
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            console.log(this.pelicula);
            await axios.post(
              "http://localhost:8080/api-movieBack/",
              this.pelicula
            );
            Swal.fire({
              title: "¡Guardada!",
              text: "La pelicula se registró correctamente",
              icon: "success",
            });
            (this.pelicula.name = ""),
              (this.pelicula.genero = ""),
              (this.pelicula.director = ""),
              (this.pelicula.publishDate = null);
            this.pelicula.description = "";
          } catch (error) {
            console.log("Error al guardar la pelicula", error);
          } finally {
            this.fetchData();
          }
        }
      });
    },
    handleDragStart(event) {
      event.dataTransfer.setData("text/plain", JSON.stringify(this.pelicula));
    },
    handleDrop(event) {
      const data = JSON.parse(event.dataTransfer.getData("text/plain"));

      this.pelicula = data;

      this.save();
    },
    fetchData() {
      axios
        .get("http://localhost:8080/api-movieBack/")
        .then((response) => {
          console.log(response.data.data);
          this.data = response.data;
          console.log("peli", response.data);
        })
        .catch((error) => {
          console.error("Error al obtener datos de la API", error);
        });
    },
    async deleteMovie(id) {
      const confirmed = await Swal.fire({
        title: "¿Estás seguro de eliminar la película?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#008c6f",
        cancelButtonColor: "#e11c24",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar",
      });

      if (confirmed.isConfirmed) {
        try {
          const response = await axios.delete(
            `http://localhost:8080/api-movieBack/peliculas/${id}`
          );
          if (response.data.error) {
            console.error(response.data.message);
          } else {
            Swal.fire({
              title: "Eliminada",
              text: "La película se eliminó correctamente",
              icon: "success",
              timer: 3000,
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
      }
    },
    onScroll() {
      const currentScrollPosition =
        window.pageYOffset || document.documentElement.scrollTop;
      console.log(currentScrollPosition);
      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 60) {
        return;
      }
      this.showElement = currentScrollPosition < this.lastScrollPosition;
      this.lastScrollPosition = currentScrollPosition;
    },
  },
  mounted() {
    this.fetchData();
    window.addEventListener("scroll", this.onScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
  },
};
</script>

<style scoped></style>
