package com.portalweb.controller.util;

import com.portalweb.entities.T004ItemPedido;

import java.util.Comparator;

//Se va a ordenar por medio de item(cod_item), tamaño(cantidad), Descripcion(descripcion),
// Extension1(descripcion_ext1), Extension2(descripcion_ext2)
public enum OrderPedidoItem implements Comparator<T004ItemPedido> {


    //CRITERIOS DE ORDENAMIENTO PARA ITEMPEDIDO.

    COD_ITEM_SORT{
        public int compare(T004ItemPedido ip1, T004ItemPedido ip2){
            return Integer.valueOf(Integer.valueOf(ip1.getCodItem()).compareTo(ip2.getCodItem()));
        }},

    TAM_CANT_SORT{
        public int compare(T004ItemPedido ip1, T004ItemPedido ip2){
            return ip1.getCantidad().compareTo(ip2.getCantidad());
        }},

    DESC_EXT1_SORT{
        public int compare(T004ItemPedido ip1, T004ItemPedido ip2){
            return ip1.getDescripcionExt1().compareTo(ip2.getDescripcionExt1());
        }},

    DESC_EXT2_SORT{
        public int compare(T004ItemPedido ip1, T004ItemPedido ip2){
            return ip1.getDescripcionExt2().compareTo(ip2.getDescripcionExt2());
        }
    },

    DESCRIPCION_SORT{
        public int compare(T004ItemPedido ip1, T004ItemPedido ip2){
            return ip1.getDescripcion().compareTo(ip2.getDescripcion());
        }};


    //Ordena de manera descendente
    public static Comparator<T004ItemPedido> ascending(final Comparator<T004ItemPedido> other){
        return new Comparator<T004ItemPedido>(){
            public int compare(T004ItemPedido ip1, T004ItemPedido ip2){
                return other.compare(ip1, ip2);
            }
        };
    }


    //Ordena de manera descendente
    public static Comparator<T004ItemPedido> decending(final Comparator<T004ItemPedido> other){
        return new Comparator<T004ItemPedido>(){
            public int compare(T004ItemPedido ip1, T004ItemPedido ip2){
                return -1 * other.compare(ip1, ip2);
            }
        };
    }



    //Realiza el proceso de comparacion.
    public static Comparator<T004ItemPedido> getComparator(final OrderPedidoItem... multipleOptions){
        return new Comparator<T004ItemPedido>() {
            @Override
            public int compare(T004ItemPedido o1, T004ItemPedido o2) {
                for (OrderPedidoItem option : multipleOptions){
                    int result = option.compare(o1, o2);
                    if (result != 0){
                        return result;
                    }
                }
                return 0;
            }
        };
    }

}
