<template>
    <div>
        <b-modal hide-footer hide-header centered id="modal-update-movie">
            <header class="text-center border-bottom">
                <p style="font-family: cabin">Editar película</p>
            </header>

            <main>
                <form id="editarPelicula">
                    <b-row>
                        <b-col>
                            <label for="pelicula">Nombre de la película: *</label>
                            <b-form-input v-model="pelicula.name" type="text" class="form-control" placeholder="Película..."
                                required></b-form-input>
                        </b-col>
                        <b-col>
                            <label for="pelicula">Género de la película: *</label>
                            <b-form-select v-model="pelicula.genero" :options="options"></b-form-select>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <label for="pelicula">Descripción de la película: *</label>
                            <b-form-textarea id="textarea" v-model="pelicula.description"
                                placeholder="Describe la película..." rows="3" max-rows="6"></b-form-textarea>
                        </b-col>
                    </b-row>
                </form>
            </main>

            <footer class="text-center mt-5">
                <button class="btn m-1 cancel" @click="onClose" id="savemovie">Cancelar</button>
                <button class="btn m-1 success" @click="save" id="savemovie" type="submit">Guardar</button>
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
        movie: {
            type: Object,
            default: () => ({
                name: '',
                description: '',
                genero: null,
            }),
        },
    },
    data() {
        return {
            pelicula: { ...this.movie },
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
        };
    },
    methods: {
        onClose() {
            this.$bvModal.hide('modal-update-movie');
            this.pelicula.name = '';
            this.pelicula.description = '';
            this.pelicula.genero = null;
        },
        async save() {
            try {
                console.log('Película a actualizar:', this.pelicula);
                Swal.fire({
                    title: 'Actualizada',
                    text: 'La película se actualizó correctamente',
                    icon: 'success',
                });
                this.onClose();
            } catch (error) {
                console.error('Error al actualizar la película', error);
            }
        },
    },
};
</script>
s
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
  