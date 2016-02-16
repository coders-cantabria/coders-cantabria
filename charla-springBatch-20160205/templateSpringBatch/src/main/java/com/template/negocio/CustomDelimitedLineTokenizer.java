/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template.negocio;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

/**
 *
 * @author rmpinedo
 */


/**
 * Se sobrescribe la clase DelimitedLineTokenizer para indicar que no trate el caracter ""
 * En algunas descripciones catalanas se incluyen ''
 * Sobreescribiendo el metodo isQuoteCharacter se evita que las confunda con el caracter ""
 * @author rmpinedo
 */
public class CustomDelimitedLineTokenizer extends DelimitedLineTokenizer {

    @Override
    protected boolean isQuoteCharacter(char c) {
        return false;
    }

}
