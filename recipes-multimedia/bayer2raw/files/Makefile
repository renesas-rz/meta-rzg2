CFLAGS := \
    $(CFLAGS) \
		-std=c99 \
		-g \
		-Wall \
		-Wextra \
		-Wshadow \
		-Wpointer-arith \
		-Wcast-qual \
		-Wcast-align \
		-Wconversion \
		-Wundef \
		-Wunused-result \
		-Wno-deprecated-declarations \
		-fPIC

CC ?= gcc
RM ?= rm -rfv
LINKFLAGS ?= -lEGL -lGLESv2
LDLIBS ?=

.PHONY: $(TARGET)
.PHONY: clean

# Path for .c , .h and .o Files
SRC_PATH ?= ./
OBJ_PATH ?= ./
OUT_PATH ?= ./
INC_PATH ?= -I ./

# Executable Name
TARGET := $(OUT_PATH)/libbayer2raw.so

# Files to compile
# ./src/main.c ./src/dlist.c
SOURCES_FILES := $(wildcard $(SRC_PATH)*.c)
OBJECTS_FILES := $(patsubst %.c, $(OBJ_PATH)%.o, $(SOURCES_FILES))

# main.o dlist.o
OBJECTS := $(notdir $(OBJECTS_FILES))

# obj/main.o obj/dlist.o
OBJ := $(patsubst %,$(OBJ_PATH)%,$(OBJECTS))

# Build .o first
# -c arg : 	Compile or assemble the source files, but do not link.
# 			The linking stage simply is not done.
# 			The ultimate output is in the form of an object
#			file for each source file.
$(OBJ_PATH)%.o: $(SRC_PATH)%.c
	@echo "[CC] $<"
	@$(CC) $(CFLAGS) $(LDLIBS) -o $@ -c $< $(INC_PATH)

# Build final Binary
$(TARGET): $(OBJ)
	@echo "[INFO] Creating Binary Executable [$(TARGET)]"
	@$(CC) -shared -o $@ $^ $(LINKFLAGS)

# Clean all the object files and the binary
clean:
	@echo "[CLEANING]"
	@$(RM) $(TARGET)
	@$(RM) $(OBJ_PATH)*.o

memory: $(TARGET)
	valgrind --leak-check=yes ./$(TARGET)
