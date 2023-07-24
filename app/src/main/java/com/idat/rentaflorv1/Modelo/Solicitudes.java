package com.idat.rentaflorv1.Modelo;

public class Solicitudes {
    private int idsolicitud;
    private int idcotizacion;
    private String numsolicitud;
    private String fechainicio;
    private String fechafin;
    private String ligarinicio;
    private String lugarfin;
    private String descripcion;
    private String motivo;
    private String estado;
    private int idtiposervicio;
    private String descripciontiposervicio;
    private int idcamioneta;
    private String descripcioncamioneta;
    private String CAPACIDAD;
    private String costo;
    private String color;
    private String clientenombre;
    private String empresanombre;
    private String ruc;
    private String clienteservicio;
    private String numero;
    private String cargoservicio;
    private String referencia;
    private String fecha;

    public Solicitudes(int idsolicitud, String numsolicitud, String fechainicio, String fechafin, String ligarinicio, String lugarfin, String descripcion, String motivo, String estado, int idtiposervicio, String descripciontiposervicio, int idcamioneta, String descripcioncamioneta) {
        this.idsolicitud = idsolicitud;
        this.numsolicitud = numsolicitud;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.ligarinicio = ligarinicio;
        this.lugarfin = lugarfin;
        this.descripcion = descripcion;
        this.motivo = motivo;
        this.estado = estado;
        this.idtiposervicio = idtiposervicio;
        this.descripciontiposervicio = descripciontiposervicio;
        this.idcamioneta = idcamioneta;
        this.descripcioncamioneta = descripcioncamioneta;
    }

    public String getClientenombre() {
        return clientenombre;
    }

    public void setClientenombre(String clientenombre) {
        this.clientenombre = clientenombre;
    }

    public String getEmpresanombre() {
        return empresanombre;
    }

    public void setEmpresanombre(String empresanombre) {
        this.empresanombre = empresanombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getClienteservicio() {
        return clienteservicio;
    }

    public void setClienteservicio(String clienteservicio) {
        this.clienteservicio = clienteservicio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCargoservicio() {
        return cargoservicio;
    }

    public void setCargoservicio(String cargoservicio) {
        this.cargoservicio = cargoservicio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Solicitudes() {
    }

    public int getIdcotizacion() {
        return idcotizacion;
    }

    public void setIdcotizacion(int idcotizacion) {
        this.idcotizacion = idcotizacion;
    }

    public int getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(int idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public String getNumsolicitud() {
        return numsolicitud;
    }

    public void setNumsolicitud(String numsolicitud) {
        this.numsolicitud = numsolicitud;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getLigarinicio() {
        return ligarinicio;
    }

    public void setLigarinicio(String ligarinicio) {
        this.ligarinicio = ligarinicio;
    }

    public String getLugarfin() {
        return lugarfin;
    }

    public void setLugarfin(String lugarfin) {
        this.lugarfin = lugarfin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdtiposervicio() {
        return idtiposervicio;
    }

    public void setIdtiposervicio(int idtiposervicio) {
        this.idtiposervicio = idtiposervicio;
    }

    public String getDescripciontiposervicio() {
        return descripciontiposervicio;
    }

    public void setDescripciontiposervicio(String descripciontiposervicio) {
        this.descripciontiposervicio = descripciontiposervicio;
    }

    public int getIdcamioneta() {
        return idcamioneta;
    }

    public void setIdcamioneta(int idcamioneta) {
        this.idcamioneta = idcamioneta;
    }

    public String getDescripcioncamioneta() {
        return descripcioncamioneta;
    }

    public void setDescripcioncamioneta(String descripcioncamioneta) {
        this.descripcioncamioneta = descripcioncamioneta;
    }

    public String getCAPACIDAD() {
        return CAPACIDAD;
    }

    public void setCAPACIDAD(String CAPACIDAD) {
        this.CAPACIDAD = CAPACIDAD;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
