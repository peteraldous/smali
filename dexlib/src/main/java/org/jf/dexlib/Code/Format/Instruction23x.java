/*
 * [The "BSD licence"]
 * Copyright (c) 2009 Ben Gruver
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.dexlib.Code.Format;

import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.ThreeRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.NumberUtils;
import org.jf.dexlib.Util.Output;

public class Instruction23x extends Instruction implements ThreeRegisterInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory();

    public static void emit(Output out, Opcode opcode, short regA, short regB, short regC) {
        if (regA >= 1 << 8 ||
                regB >= 1 << 8 ||
                regC >= 1 << 8) {
            throw new RuntimeException("The register number must be less than v256");
        }

        out.writeByte(opcode.value);
        out.writeByte(regA);
        out.writeByte(regB);
        out.writeByte(regC);
    }

    private Instruction23x(Opcode opcode, byte[] buffer, int bufferIndex) {
        super(opcode, buffer, bufferIndex);
    }

    public Format getFormat() {
        return Format.Format23x;
    }

    public int getRegisterA() {
        return NumberUtils.decodeUnsignedByte(buffer[bufferIndex + 1]);
    }

    public int getRegisterB() {
        return NumberUtils.decodeUnsignedByte(buffer[bufferIndex + 2]);
    }

    public int getRegisterC() {
        return NumberUtils.decodeUnsignedByte(buffer[bufferIndex + 3]);
    }

    private static class Factory implements Instruction.InstructionFactory {
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] buffer, int bufferIndex) {
            return new Instruction23x(opcode, buffer, bufferIndex);
        }
    }
}
