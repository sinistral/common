/* Copyright (C) 2010 Center for Computational Pharmacology, University of Colorado School of Medicine
 * 
 * This file is part of the CCP common library.
 * The CCP common library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package edu.ucdenver.ccp.common.string;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ucdenver.ccp.common.string.StringBufferUtil;

public class StringBufferUtilTest {

	@Test
	public void testPrependToStringBuffer() throws Exception {
		String originalText = "This is some text.";
		String prependedText = "Prepended Text Here.";
		StringBuffer sb = new StringBuffer();
		sb.append(originalText);
		assertEquals(String.format("Should only have originalText at this point."), sb.toString(), originalText);
		sb = StringBufferUtil.prepend(sb, prependedText);
		assertEquals(String.format("Should now have prepended text at the beginning."), sb.toString(), prependedText
				+ originalText);
	}

}
