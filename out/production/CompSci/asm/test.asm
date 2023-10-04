; ----------------------------------------------------------------------------
; test.asm
;
; This is a Win32 console program that writes "Hello, World" on one line and
; then exits.  It needs to be linked with a C library.
; ----------------------------------------------------------------------------

    global  _main
    extern  _printf

    section .text
_main:
    ; Print our "Hello, World" message
    push    message
    call    _printf
    add     esp, 4
    ; --------------------------------
    ; Print our second message
    push    message_two
    call    _printf
    add     esp, 4
    ; --------------------------------
    ; Pause the program forever :3
    wait_program:
    nop ; Waste a few cycles doing nothing.
    jmp wait_program ; Jump back to nop instruction
message:
    db  'Hello, World!', 10, 0
message_two:
    db  'Hello :3', 10, 0