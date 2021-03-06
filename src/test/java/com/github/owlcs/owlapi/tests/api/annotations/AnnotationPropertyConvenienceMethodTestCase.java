/*
 * This file is part of the ONT API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright (c) 2019, The University of Manchester, owl.cs group.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.owlcs.owlapi.tests.api.annotations;

import com.github.owlcs.owlapi.tests.api.baseclasses.TestBase;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.Filters;

import java.util.Collection;

import static com.github.owlcs.owlapi.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Filters.subAnnotationWithSuper;
import static org.semanticweb.owlapi.search.Searcher.sub;
import static org.semanticweb.owlapi.search.Searcher.sup;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 */
public class AnnotationPropertyConvenienceMethodTestCase extends TestBase {

    @Test
    public void testGetSuperProperties() {
        OWLOntology ont = getOWLOntology();
        OWLAnnotationProperty propP = AnnotationProperty(iri("propP"));
        OWLAnnotationProperty propQ = AnnotationProperty(iri("propQ"));
        OWLAnnotationProperty propR = AnnotationProperty(iri("propR"));
        ont.getOWLOntologyManager().addAxiom(ont,
                df.getOWLSubAnnotationPropertyOfAxiom(propP, propQ));
        ont.getOWLOntologyManager().addAxiom(ont,
                df.getOWLSubAnnotationPropertyOfAxiom(propP, propR));
        Collection<OWLAxiom> axioms = asUnorderedSet(
                ont.axioms(Filters.subAnnotationWithSub, propP, INCLUDED));
        assertTrue(contains(sup(axioms.stream()), propQ));
        assertTrue(contains(sup(axioms.stream()), propR));
        axioms = asUnorderedSet(
                ont.axioms(Filters.subAnnotationWithSub, propP, EXCLUDED));
        assertTrue(contains(sup(axioms.stream()), propQ));
        assertTrue(contains(sup(axioms.stream()), propR));
    }

    @Test
    public void testGetSubProperties() {
        OWLOntology ont = getOWLOntology();
        OWLAnnotationProperty propP = AnnotationProperty(iri("propP"));
        OWLAnnotationProperty propQ = AnnotationProperty(iri("propQ"));
        OWLAnnotationProperty propR = AnnotationProperty(iri("propR"));
        ont.getOWLOntologyManager().addAxiom(ont,
                df.getOWLSubAnnotationPropertyOfAxiom(propP, propQ));
        ont.getOWLOntologyManager().addAxiom(ont,
                df.getOWLSubAnnotationPropertyOfAxiom(propP, propR));
        assertTrue(contains(
                sub(ont.axioms(subAnnotationWithSuper, propQ, INCLUDED)), propP));
        assertTrue(contains(
                sub(ont.axioms(subAnnotationWithSuper, propQ, EXCLUDED)), propP));
        assertTrue(contains(
                sub(ont.axioms(subAnnotationWithSuper, propR, INCLUDED)), propP));
        assertTrue(contains(
                sub(ont.axioms(subAnnotationWithSuper, propR, EXCLUDED)), propP));
    }
}
