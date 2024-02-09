<template>
    <div>
        <b-modal hide-footer hide-header centered id="modal-update-movie">
            <header class="text-center border-bottom">
                <p style="font-family: cabin">Editar película</p>
            </header>

            <main>
                <form id="editarPelicula" @submit.prevent="updateMovie">
                    <b-row>
                        <b-col>
                            <label for="pelicula">Nombre de la película: *</label>
                            <b-form-input v-model="editedMovie.name" type="text" class="form-control"
                                placeholder="Película..." required></b-form-input>
                        </b-col>
                        <b-col>
                            <label for="pelicula">Género de la película: *</label>
                            <b-form-select v-model="editedMovie.genero" :options="options"></b-form-select>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <label for="pelicula">Descripción de la película: *</label>
                            <b-form-textarea v-model="editedMovie.description" id="textarea"
                                placeholder="Describe la película..." rows="3" max-rows="6"></b-form-textarea>
                        </b-col>
                    </b-row>
                    <b-button type="submit" class="d-none"></b-button>
                </form>
            </main>

            <footer class="text-center mt-5">
                <button class="btn m-1 cancel" @click="onClose" id="savemovie">Cancelar</button>
                <button class="btn m-1 success" @click="updateMovie" type="submit">Actualizar</button>
            </footer>
        </b-modal>
    </div>
</template>

<script>
import Swal from 'sweetalert2';
import axios from 'axios';

export default {
    name: 'ModalUpdateMovie',
    props: {
        movie: Object,
    },
    data() {
        return {
            options: [
                { value: null, text: 'Selecciona una opción' },
                { value: 'Terror', text: 'Terror' },
                { value: 'Aventura', text: 'Aventura' },
                { value: 'Acción', text: 'Acción' },
                { value: 'Catástrofe', text: 'Catástrofe' },
                { value: 'Ciencia Ficción', text: 'Ciencia Ficción' },
                { value: 'Comedia', text: 'Comedia' },
                { value: 'Documentales', text: 'Documentales' },
                { value: 'Drama', text: 'Drama' },
            ],
            editedMovie: {
                name: '',
                genero: null,
                description: '',
            },
        };
    },
    methods: {
        onClose() {
            this.$bvModal.hide('modal-update-movie');
        },
        async updateMovie() {
            try {
                Swal.fire({
                    title: "¿Estás seguro de actualizar la película?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                }).then(async (result) => {
                    if (result.isConfirmed) {
                        console.log("Pelicula a editar: ",this.editedMovie);
                        await axios.put(`http://localhost:8090/api-movieBack/`, this.editedMovie);

                        Swal.fire({
                            title: 'Actualizada',
                            text: 'La película se actualizó correctamente',
                            icon: 'success',
                        });

                        this.onClose();
                        this.$emit('movie-updated');  // Emitir un evento para notificar al componente padre
                    }
                });
            } catch (error) {
                console.error('Error al actualizar la película', error);
                // Puedes mostrar un mensaje de error utilizando una librería de notificación o simplemente consola
            }
        },
    },
    watch: {
        movie: {
            handler(newVal) {
                // Actualizar el objeto editedMovie cuando cambia la película seleccionada
                this.editedMovie = { ...newVal };
            },
            immediate: true,
        },
    },
};
</script>

<style scoped>
.success {
    font-family: Cabin;
    background-color: #009475;
    color: white;
}

.cancel {
    font-family: Cabin;
    background-color: #ffce50;
    color: black;
}
</style>
  